import { Component } from '@angular/core';
import {UserExam} from '../../models/user-exam';
import {UserExamService} from '../../services/user-exam.service';
import {AuthService} from '../../services/auth';
import {CommonModule} from '@angular/common';
import {ExamCardComponent} from '../exam-card/exam-card';
import {ExamPopUp} from '../exam-pop-up/exam-pop-up';
import {ElectiveExamPopupComponent} from '../elective-exam-popup/elective-exam-popup';
import {CourseService} from '../../services/course.service';

@Component({
  selector: 'app-libretto',
  imports: [CommonModule, ExamCardComponent, ExamPopUp, ElectiveExamPopupComponent],
  templateUrl: './libretto.html',
  styleUrl: './libretto.css',
})
export class LibrettoComponent {

  exams : UserExam[] = []; // l'array dove conserviamo tutti gli esami
  selectedExam: UserExam | null = null; // l'esame selezionato da modificare o null se non selezionato
  showPopUp: boolean = false; // lo stato del popUp che impostiamo a invisible
  errorMessage: string = '';
  showElectivePopup: boolean = false;  // stato del popup esami elettivi
  userCourseId: number = 0;  // id del corso dell'utente

  constructor(private userExamService: UserExamService, private authService: AuthService, private courseService: CourseService) { }

  ngOnInit(){
    this.loadExams();
  }

  openPopUp(exam: UserExam){
    this.selectedExam = exam; // per ricordare l'esame selezionato ce lo salviamo
    this.showPopUp = true; // mostriamo il popUp cambiando da nascosto a visibile
  }

  closePopup() {
    this.showPopUp = false; // cambiamo lo stato del popUp da visibile a nascosto
    this.selectedExam = null; //deselezioniamo l'esame
    this.errorMessage = ''; //resettiamo l'errore
  }

  openElectivePopup() {
    this.showElectivePopup = true; //apriamo il popUp degli esami a scelta
  }

  closeElectivePopup() {
    this.showElectivePopup = false; //chiudiamo il popUp degli esami a scelta
  }

  saveElectiveSelection(examIds: number[]) {
    // Recuperiamo l'utente loggato
    this.authService.checkSession().subscribe({
      next: (user: any) => {
        // Chiamiamo il service per salvare
        this.userExamService.updateElectiveSelection(user.id, examIds).subscribe({
          next: () => {
            console.log('Succesfully saved elective exams!');
            this.loadExams(); // Ricarica la lista per mostrare i nuovi esami
            this.closeElectivePopup(); // Chiudi il popup
          },
        });
      }
    });
  }



  saveExam(exam: UserExam) {
    // Reset errore precedente
    this.errorMessage = '';

    // Andiamo a fare un controllo sul voto se non valido restituiamo un errore
    if (exam.grade !== null && exam.grade !== undefined) {
      if (exam.grade < 18 || exam.grade > 30) {
        this.errorMessage = 'Il voto deve essere compreso tra 18 e 30';
        return;
      }
    }

    // Cambiamo lo status dell'esame da not_taken a passed
    if (exam.grade && exam.grade >= 18 && exam.grade <= 30) {
      exam.status = 'PASSED';
    }

    //chiamata al servizio per modificare l'esame
    this.userExamService.updateExam(exam.id, exam).subscribe({
      next: () => {
        this.loadExams();
        this.closePopup();
      },
      error: (err) => {
        this.errorMessage = 'Error modifying exam';
      }
    })

  }

  //Verifichiamo chi è loggato e carichiamo gli esami associati all'utente
  loadExams() {
    this.authService.checkSession().subscribe({
      next: (user: any) => {
        // Carichiamo gli esami dell'utente
        this.userExamService.getUsersExams(user.id).subscribe({
          next: (res) => {
            // Mostriamo gli esami che NON sono a scelta o quelli a scelta che sono stati selezionati
            this.exams = res.filter(exam => !exam.isElective || exam.isSelected);
          }
        });

        // Carichiamo l'ID del corso per il popup degli esami a scelta
        this.courseService.getUserSelection(user.id).subscribe({
          next: (selection: any) => {
            this.userCourseId = selection.course_id;
          }
        });
      }
    });
  }

}

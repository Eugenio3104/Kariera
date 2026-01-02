import { Component, EventEmitter, Input, Output, OnChanges } from '@angular/core';
import { CourseExam } from '../../models/study-area-course.model';
import { CommonModule } from '@angular/common';
import { CourseService } from '../../services/course.service';


@Component({
  selector: 'app-elective-exam-popup',
  imports: [CommonModule],
  templateUrl: './elective-exam-popup.html',
  styleUrl: './elective-exam-popup.css',
})
export class ElectiveExamPopupComponent implements OnChanges {

  constructor(private courseService: CourseService) { }


  @Input() openPopup: boolean = false;

  @Input() courseId: number = -1;

  @Output() closePopup = new EventEmitter<void>();

  @Output() saveSelection = new EventEmitter<number[]>();

  electiveExams: CourseExam[] = [] //array di esami a scelta
  selectedExams: number[] = []; //array di esami selezionati
  totalCfu: number = 0; //numero totale di cfu selezionati
  errorMessage: string = ''; // messaggio di errore se si supera il limite di cfu selezionati

  ngOnChanges() {
    // Se il popup è aperto e abbiamo un ID del corso valido
    if (this.openPopup && this.courseId !== -1) {
      // Resettiamo le selezioni vecchie
      this.selectedExams = [];
      this.totalCfu = 0;
      this.errorMessage = '';
      // Carichiamo gli esami
      this.loadElectiveExams();
    }
  }

  // Carica gli esami a scelta per il corso selezionato
  loadElectiveExams() {
    this.errorMessage = '';
    this.courseService.getElectiveExams(this.courseId).subscribe({
      next: (data) => {
        this.electiveExams = data;
      }
    })
  }

  isSelected(examId: number): boolean {
    return this.selectedExams.includes(examId);
  }

  toggleSelection(examId: number, cfu: number) {
    if (this.isSelected(examId)) {
      // Deselezione: rimuovi l'esame e sottrai i CFU
      this.selectedExams = this.selectedExams.filter(id => id !== examId);
      this.totalCfu -= cfu;
      this.errorMessage = '';
    } else {
      // Selezione: controlla se si superano i 15 CFU
      if (this.totalCfu + cfu > 15) {
        this.errorMessage = 'Non puoi superare i 15 CFU totali.';
      } else {
        this.selectedExams.push(examId);
        this.totalCfu += cfu;
        this.errorMessage = '';
      }
    }
  }

  //salviamo la selezione  degli esami e la mandiamo al padre e chiudiamo il popup
  save() {
    this.saveSelection.emit(this.selectedExams);
    this.closePopup.emit();
  }

  close() {
    this.closePopup.emit();
  }

}

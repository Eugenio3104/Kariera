import { Component, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserExam } from '../../models/user-exam';
@Component({
  selector: 'app-exam-pop-up',
  imports: [CommonModule, FormsModule],
  templateUrl: './exam-pop-up.html',
  styleUrl: './exam-pop-up.css',
})
export class ExamPopUp implements OnChanges {

  @Input() exam: UserExam | null = null; //riceviamo l'esame da modificare dal componente padre

  @Input() showPopUp: boolean = false; //lo stato del popUp che impostiamo a invisible

  @Input() errorMessage: string = ''; //il messaggio di errore da mandare al componente padre

  @Output() closePopup = new EventEmitter<void>(); //inviamo al padre il fatto che il popUp deve essere chiuso

  @Output() saveExam = new EventEmitter<UserExam>(); //inviamo al padre il fatto che l'esame deve essere salvato

  examCopy: UserExam | null = null; //copia dell'esame da modificare per evitare di modificare l'esame originale


  ngOnChanges() {
    if (this.exam && this.showPopUp) {
      this.examCopy = { ...this.exam };
    }
  }

  close() {
    this.closePopup.emit();
  }

  save() {
    if (this.examCopy) {
      this.saveExam.emit(this.examCopy);
    }
  }


}

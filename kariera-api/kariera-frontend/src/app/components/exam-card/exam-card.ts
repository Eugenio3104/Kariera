import { Component , Input , Output , EventEmitter} from '@angular/core';
import {UserExam} from '../../models/user-exam';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-exam-card',
  imports: [CommonModule],
  templateUrl: './exam-card.html',
  styleUrl: './exam-card.css',
})
export class ExamCardComponent {
    @Input() exam!: UserExam;

    @Output() examClicked = new EventEmitter<UserExam>();

    onExamClick(){
       this.examClicked.emit(this.exam);
    }
}

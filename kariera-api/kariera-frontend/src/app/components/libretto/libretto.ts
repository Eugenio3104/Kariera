import { Component } from '@angular/core';
import {UserExam} from '../../models/user-exam';
import {UserExamService} from '../../services/user-exam.service';
import {AuthService} from '../../services/auth';
import {CommonModule} from '@angular/common';
import {ExamCardComponent} from '../exam-card/exam-card';

@Component({
  selector: 'app-libretto',
  imports: [CommonModule , ExamCardComponent],
  templateUrl: './libretto.html',
  styleUrl: './libretto.css',
})
export class LibrettoComponent {

  exams : UserExam[] = [];

  constructor(private userExamService: UserExamService , private authService: AuthService){}

  ngOnInit(){
    this.authService.checkSession().subscribe({
      next: (user: any) => {
        this.userExamService.getUsersExams(user.id).subscribe({
          next: (res) => {
            this.exams = res.filter(exam=>!exam.isElective);
          }
        });
      }
    })
  }

  openPopUp(exam: UserExam){
    console.log(exam);
  }

}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserExam} from '../models/user-exam';

@Injectable({
  providedIn: 'root'
})

export class UserExamService {

  private apiUrl = 'http://localhost:8080/api';
  constructor(private http : HttpClient) { }

  getUsersExams(userId: number):Observable<UserExam[]>{
    return this.http.get<UserExam[]>(`${this.apiUrl}/user-exam-results/by-user/${userId}/details`, {withCredentials: true});
  }

}

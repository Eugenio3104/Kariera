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
  // Recupera i dettagli degli esami di un utente specifico
  getUsersExams(userId: number):Observable<UserExam[]>{
    return this.http.get<UserExam[]>(`${this.apiUrl}/user-exam-results/by-user/${userId}/details`, {withCredentials: true});
  }
  // Aggiorna le informazioni di un singolo esame (es. voto o stato)
  updateExam(examId: number, exam: UserExam): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/user-exam-results/${examId}`, exam, { withCredentials: true });
  }
  // Salva la scelta degli esami a scelta effettuata dall'utente
  updateElectiveSelection(userId: number, examIds: number[]): Observable<void> {
    const body = {
      userId: userId,
      electiveExamIds: examIds
    };
    return this.http.put<void>(`${this.apiUrl}/user-exam-results/elective-selection`, body, { withCredentials: true });
  }

}

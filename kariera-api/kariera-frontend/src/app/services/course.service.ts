import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CourseExam, StudyAreaWithCourses } from '../models/study-area-course.model';
@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }
  // Recupera tutte le aree di studio insieme ai relativi corsi disponibili
  getAllStudyAreasWithCourses(): Observable<StudyAreaWithCourses[]> {
    return this.http.get<StudyAreaWithCourses[]>(`${this.apiUrl}/study-area/with-courses`,{ withCredentials: true });
  }
  // Salva l'associazione tra un utente e il corso di laurea scelto
  saveUserCourseSelection(userId: number, courseId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/user-selections?userId=${userId}&courseId=${courseId}`, null, { withCredentials: true });
  }
  // Recupera la selezione del corso effettuata da uno specifico utente
  getUserSelection(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user-selections/${userId}`, { withCredentials: true });
  }
  // Recupera la lista degli esami a scelta disponibili per un determinato corso
  getElectiveExams(courseId: number): Observable<CourseExam[]> {
    return this.http.get<CourseExam[]>(`${this.apiUrl}/course-exams/by-course/${courseId}/elective`, { withCredentials: true });
  }

}

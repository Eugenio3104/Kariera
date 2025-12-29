import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudyAreaWithCourses } from '../models/study-area-course.model';
@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }
  // GET /api/study-area/with-courses
  getAllStudyAreasWithCourses(): Observable<StudyAreaWithCourses[]> {
    return this.http.get<StudyAreaWithCourses[]>(
      `${this.apiUrl}/study-area/with-courses`,
      { withCredentials: true }
    );
  }
  // POST /api/user-selections
  saveUserCourseSelection(userId: number, courseId: number): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/user-selections?userId=${userId}&courseId=${courseId}`,
      null,
      { withCredentials: true }
    );
  }

  // GET /api/user-selections/{userId}
  getUserSelection(userId: number): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/user-selections/${userId}`,
      { withCredentials: true }
    );
  }
}

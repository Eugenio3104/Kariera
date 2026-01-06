import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activity } from '../models/activity.model';

//COMMENTO
@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  private apiUrl = 'http://localhost:8080/api/calendar/activities';

  constructor(private http: HttpClient) {}

  getAllActivities(): Observable<Activity[]> {
    return this.http.get<Activity[]>(this.apiUrl, { withCredentials: true });
  }

  createActivity(activity: Activity): Observable<Activity> {
    return this.http.post<Activity>(this.apiUrl, activity, { withCredentials: true });
  }

  updateActivity(id: number, activity: Activity): Observable<Activity> {
    return this.http.put<Activity>(`${this.apiUrl}/${id}`, activity, { withCredentials: true });
  }

  deleteActivity(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      withCredentials: true,
      responseType: 'json'
    });
  }
}

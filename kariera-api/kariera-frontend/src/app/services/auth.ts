import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

//COMMENTO
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private auth_api = 'http://localhost:8080/api/auth';
  private userLogged: User | null = null;

  constructor(private http: HttpClient) { }

  login(credentials: any) {
    return this.http.post(this.auth_api + '/login', {
      email: credentials.email,
      password: credentials.password
    }, { withCredentials: true });
  }

  registration(userData : User){
    return this.http.post(this.auth_api + '/register', userData , { withCredentials: true });
  }

  isLoggedIn():boolean{
    return this.userLogged != null;
  }

  setLoggedUser(user: any){
    this.userLogged = user;
  }

  checkSession() {
    return this.http.get(this.auth_api + '/me', { withCredentials: true });
  }

  logout(){
    return this.http.post(this.auth_api + '/logout', {} , { withCredentials: true });
  }

  clearLoggedUser(){
    this.userLogged = null;
  }
}

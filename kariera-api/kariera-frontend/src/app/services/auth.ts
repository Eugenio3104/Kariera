import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private auth_api = 'http://localhost:8080/api/auth';

  private userLogged: any = null;

  constructor(private http: HttpClient) { }

  //metodo per effettuare il login
  login(credentials: any) {
    return this.http.post(this.auth_api + '/login', credentials ,  {withCredentials: true});
  }

  //metodo per effettuare la registrazione
  registration(userData : any){
    return this.http.post(this.auth_api + '/register', userData);
  }

  //metodo per il guardiano
  isLoggedIn():boolean{
    return this.userLogged != null;
  }

  //metodo per salvare l'utente loggato'
  setLoggedUser(user: any){
    this.userLogged = user;
  }

}

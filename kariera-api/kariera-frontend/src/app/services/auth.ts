import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model'; // Importi il tuo modello

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private auth_api = 'http://localhost:8080/api/auth';

  private userLogged: User | null = null;

  constructor(private http: HttpClient) { }

  //metodo per effettuare il login
  login(credentials: any) {

    return this.http.post(this.auth_api + '/login', {
      email: credentials.email,
      password: credentials.password
    }, { withCredentials: true });
  }

  //metodo per effettuare la registrazione
  registration(userData : User){
    return this.http.post(this.auth_api + '/register', userData , { withCredentials: true });
  }

  //metodo per il guardiano
  isLoggedIn():boolean{
    return this.userLogged != null;
  }

  //metodo per salvare l'utente loggato'
  setLoggedUser(user: any){
    this.userLogged = user;
  }

  //metodo per verificare se c'è una sessione attiva
  checkSession() {
    return this.http.get(this.auth_api + '/me', { withCredentials: true });
  }

}

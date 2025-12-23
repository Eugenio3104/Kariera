import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Login} from '../../models/login.model';

@Component({
  selector: 'app-login',
  imports: [
    RouterLink,
    FormsModule,
    CommonModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {

  login:Login = {email:'', password:''};

  constructor(private authService: AuthService , private router: Router) {}

  onLogin(){
    this.authService.login(this.login).subscribe({
      next: (res) => {
        this.authService.setLoggedUser(res);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        alert('Email o password errati');
      }
    })
  }
}

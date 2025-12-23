import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

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
  login = { email: '', password: '' };
  errorMessage: string = '';
  showPassword: boolean = false;

  constructor(private authService: AuthService , private router: Router) {}

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onLogin(){
    this.errorMessage = '';

    this.authService.login(this.login).subscribe({
      next: (res) => {
        this.authService.setLoggedUser(res);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        if (err.status === 401 || err.status === 400) {
          this.errorMessage = "This account is not registered. Please check your email or sign up.";
        } else {
          this.errorMessage = "Invalid email or password  . Please try again later.";
        }
      }
    });
  }
}

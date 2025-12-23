import { Component } from '@angular/core';
import {User} from '../../models/user.model';
import {AuthService} from '../../services/auth';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [FormsModule, CommonModule , RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {

  user:User = {name:'', surname:'', email:'', password:''};
  errorMessage: string = '';

  constructor(private authService: AuthService ,private router: Router) { }

  onRegister() {
    this.errorMessage = '';
    this.authService.registration(this.user).subscribe({
      next: (res) => {
        this.router.navigate(['/courses']);
      },
      error: (err) => {
        if (err.status === 400) {
          this.errorMessage = err.error?.error || "This email address is already associated with an account. Please try logging in.";
        } else {
          this.errorMessage = "A system error occurred. Please try again later.";
        }
      }
    });
  }
}

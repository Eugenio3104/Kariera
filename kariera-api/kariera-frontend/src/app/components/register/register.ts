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

  constructor(private authService: AuthService ,private router: Router) { }

  onRegister() {
    this.authService.registration(this.user).subscribe({
      next: (res) => {
        alert('Registrazione completata con successo!');
        this.router.navigate(['/courses']);
      },
      error: (err) => {
        console.error(err);
        alert('Errore nella registrazione: ' + (err.error || 'Riprova più tardi'));
      }
    });
  }
}

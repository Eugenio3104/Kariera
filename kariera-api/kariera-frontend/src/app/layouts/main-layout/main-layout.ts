import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css'
})
export class MainLayoutComponent implements OnInit {
  currentUser: any = null;

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    // Carica l'utente dal AuthService (già caricato dall'authGuard)
    if (this.authService.isLoggedIn()) {
      // L'utente è stato salvato nel service dopo il login
      this.authService.checkSession().subscribe({
        next: (user: any) => {
          this.currentUser = user;
        },
        error: () => {
          // Sessione scaduta, redirect al login
          this.router.navigate(['/login']);
        }
      });
    }
  }

  getInitials(): string {
    if (!this.currentUser) return '?';
    const firstInitial = this.currentUser.name?.charAt(0) || '';
    const lastInitial = this.currentUser.surname?.charAt(0) || '';
    return (firstInitial + lastInitial).toUpperCase();
  }


}

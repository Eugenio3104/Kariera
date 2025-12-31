import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CourseService } from '../../services/course.service';
import {Router, RouterLink} from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-courses',
  imports: [CommonModule, FormsModule],
  templateUrl: './courses.html',
  styleUrl: './courses.css',
})
export class CoursesComponent implements OnInit {

  studyAreas: any[] = [];
  selectedCourseId: any = null;
  loading = true;
  error = false;
  userId = 0;
  successMessage = '';
  errorMessage = '';

  constructor(
    private courseService: CourseService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    // prendo user id
    this.authService.checkSession().subscribe({
      next: (user: any) => {
        this.userId = user.id;
      }
    });

    // carico le aree di studio
    this.courseService.getAllStudyAreasWithCourses().subscribe({
      next: (data) => {
        this.studyAreas = data;
        this.loading = false;
      },
      error: (err) => {
        console.log('Error:', err);
        this.error = true;
        this.loading = false;
      }
    });
  }

  selectCourse(courseId: number) {
    this.selectedCourseId = courseId;
    this.successMessage = '';
    this.errorMessage = '';
  }

  saveSelection() {
    if (!this.selectedCourseId) {
      this.errorMessage = 'Please select a course!';
      return;
    }

    this.courseService.saveUserCourseSelection(this.userId, this.selectedCourseId).subscribe({
      next: () => {
        this.successMessage = 'Selection saved successfully!';
        this.errorMessage = '';
        this.router.navigate(['/app/dashboard']);
      },
      error: (err) => {
        console.log('Error saving:', err);
        this.errorMessage = 'Error saving selection';
        this.successMessage = '';
      }
    });
  }
}

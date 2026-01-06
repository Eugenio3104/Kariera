import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarService } from '../../services/calendar.service';
import { Activity } from '../../models/activity.model';

@Component({
  selector: 'app-class-schedule',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './class-schedule.html',
  styleUrls: ['./class-schedule.css']
})
export class ClassScheduleComponent implements OnInit {

  activities: Activity[] = [];
  days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  timeSlots: string[] = [
    '08:00', '09:00', '10:00', '11:00', '12:00',
    '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'
  ];

  currentWeek: Date[] = [];
  showModal = false;
  isEditMode = false;
  errorMessage: string = '';

  activityForm: Activity = {
    startTime: '',
    endTime: '',
    date: '',
    courseName: '',
    room: '',
    professor: ''
  };

  constructor(private calendarService: CalendarService) {}

  ngOnInit(): void {
    this.loadCurrentWeek();
    this.loadActivities();
  }

  loadCurrentWeek(): void {
    const today = new Date();
    const dayOfWeek = today.getDay();
    const monday = new Date(today);
    const diff = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
    monday.setDate(today.getDate() + diff);

    this.currentWeek = [];
    for (let i = 0; i < 6; i++) {
      const day = new Date(monday);
      day.setDate(monday.getDate() + i);
      this.currentWeek.push(day);
    }
  }

  changeWeek(direction: number): void {
    const firstDay = this.currentWeek[0];
    firstDay.setDate(firstDay.getDate() + (direction * 7));

    this.currentWeek = [];
    for (let i = 0; i < 6; i++) {
      const day = new Date(firstDay);
      day.setDate(firstDay.getDate() + i);
      this.currentWeek.push(day);
    }
  }

  goToCurrentWeek(): void {
    this.loadCurrentWeek();
  }

  loadActivities(): void {
    this.calendarService.getAllActivities().subscribe({
      next: (data) => {
        this.activities = data;
      },
      error: (err) => {
        console.error('Error loading activities:', err);
        alert('Unable to load activities. Please check authentication.');
      }
    });
  }

  getActivitiesForSlot(day: Date, timeSlot: string): Activity[] {
    const dateStr = this.formatDate(day);
    return this.activities.filter(activity => {
      const activityStart = activity.startTime.substring(0, 5);
      return activity.date === dateStr && activityStart === timeSlot;
    });
  }

  calculateActivityHeight(activity: Activity): number {
    const startTime = activity.startTime.substring(0, 5);
    const endTime = activity.endTime.substring(0, 5);

    const [startHour, startMin] = startTime.split(':').map(Number);
    const [endHour, endMin] = endTime.split(':').map(Number);

    const startTotalMinutes = startHour * 60 + startMin;
    const endTotalMinutes = endHour * 60 + endMin;

    const durationMinutes = endTotalMinutes - startTotalMinutes;
    const durationHours = durationMinutes / 60;

    const slotHeight = 81;

    // Con margin: 8px su tutti i lati, riduciamo compensation
    // 65 - 16px (margin top + bottom) = 49
    const baseHeight = (durationHours * slotHeight) - 1;
    const compensation = 49; //Ridotto da 65 a 49 per compensare margin

    return baseHeight + compensation;
  }




  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  openCreateModal(day: Date, timeSlot: string): void {
    this.isEditMode = false;

    // Calcola ora di fine (1 ora dopo l'inizio)
    const startHour = parseInt(timeSlot.split(':')[0]);
    const endHour = startHour + 1;
    const endTime = `${String(endHour).padStart(2, '0')}:00:00`;

    this.activityForm = {
      startTime: `${timeSlot}:00`,
      endTime: endTime,
      date: this.formatDate(day),
      courseName: '',
      room: '',
      professor: ''
    };
    this.showModal = true;
  }

  openEditModal(activity: Activity): void {
    this.isEditMode = true;
    this.activityForm = { ...activity };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.activityForm = {
      startTime: '',
      endTime: '',
      date: '',
      courseName: '',
      room: '',
      professor: ''
    };
  }

  saveActivity(): void {
    if (!this.validateForm()) {
      alert('Please fill all required fields!');
      return;
    }

    // Assicurati che gli orari abbiano il formato corretto (HH:mm:ss)
    if (this.activityForm.startTime.length === 5) {
      this.activityForm.startTime += ':00';
    }
    if (this.activityForm.endTime.length === 5) {
      this.activityForm.endTime += ':00';
    }

    if (this.isEditMode && this.activityForm.id) {
      this.calendarService.updateActivity(this.activityForm.id, this.activityForm).subscribe({
        next: (updated) => {
          const index = this.activities.findIndex(a => a.id === updated.id);
          if (index !== -1) {
            this.activities[index] = updated;
          }
          this.closeModal();
        },
        error: (err) => {
          console.error('Error updating activity:', err);
          alert('Error updating activity.');
        }
      });
    } else {
      this.calendarService.createActivity(this.activityForm).subscribe({
        next: (created) => {
          this.activities.push(created);
          this.closeModal();
        },
        error: (err) => {
          console.error('Error creating activity:', err);
          alert('Error creating activity.');
        }
      });
    }
  }

  deleteActivity(activity: Activity): void {
    if (!activity.id) return;

    if (confirm(`Delete activity "${activity.courseName}"?`)) {
      this.calendarService.deleteActivity(activity.id).subscribe({
        next: () => {
          this.activities = this.activities.filter(a => a.id !== activity.id);
        },
        error: (err) => {
          console.error('Error deleting activity:', err);
          alert('Error deleting activity.');
        }
      });
    }
  }

  validateForm(): boolean {
    return !!(
      this.activityForm.startTime &&
      this.activityForm.endTime &&
      this.activityForm.date &&
      this.activityForm.courseName &&
      this.activityForm.room &&
      this.activityForm.professor
    );
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarService } from '../../services/calendar.service';
import { Activity } from '../../models/activity.model';

//COMMENTO

@Component({
  selector: 'app-class-schedule',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './class-schedule.html',
  styleUrls: ['./class-schedule.css']
})
export class ClassScheduleComponent implements OnInit {

  activities: Activity[] = [];
  days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  timeSlots: string[] = [
    '08:00', '09:00', '10:00', '11:00', '12:00',
    '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'
  ];

  currentWeek: Date[] = [];
  showModal = false;
  isEditMode = false;
  errorMessage: string = '';

  activityForm: Activity = {
    id: undefined,
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
    for (let i = 0; i < 5; i++) {
      const day = new Date(monday);
      day.setDate(monday.getDate() + i);
      this.currentWeek.push(day);
    }
  }

  changeWeek(direction: number): void {
    const firstDay = new Date(this.currentWeek[0]);
    firstDay.setDate(firstDay.getDate() + (direction * 7));

    this.currentWeek = [];
    for (let i = 0; i < 5; i++) {
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
        console.log('✅ Activities loaded:', data);
      },
      error: (err) => {
        console.error('❌ Error loading activities:', err);
        if (err.status === 0) {
          console.warn('⚠️ Backend not available');
        }
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

    const durationMinutes = (endHour * 60 + endMin) - (startHour * 60 + startMin);
    const durationHours = durationMinutes / 60;

    const cellWhiteHeight = 101;
    const gapHeight = 10;
    const cardPadding = 8;

    const totalHeight = (durationHours * cellWhiteHeight) + ((durationHours - 1) * gapHeight) - cardPadding;

    return Math.round(totalHeight);
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  openCreateModal(day: Date, timeSlot: string): void {
    this.isEditMode = false;
    this.errorMessage = '';

    const startHour = parseInt(timeSlot.split(':')[0]);
    const endHour = startHour + 1;
    const endTime = `${String(endHour).padStart(2, '0')}:00:00`;

    this.activityForm = {
      id: undefined,
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
    console.log('📝 Opening edit modal for:', activity);
    console.log('📝 Activity ID:', activity.id);
    this.isEditMode = true;
    this.errorMessage = '';
    this.activityForm = { ...activity };
    console.log('📝 ActivityForm after copy:', this.activityForm);
    console.log('📝 ActivityForm ID:', this.activityForm.id);
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.errorMessage = '';
    this.activityForm = {
      id: undefined,
      startTime: '',
      endTime: '',
      date: '',
      courseName: '',
      room: '',
      professor: ''
    };
  }

  saveActivity(): void {
    console.log('💾 Save called - isEditMode:', this.isEditMode);
    console.log('💾 ActivityForm:', this.activityForm);
    console.log('💾 ActivityForm ID:', this.activityForm.id);

    if (!this.validateForm()) {
      return;
    }

    if (this.checkOverlap(this.activityForm)) {
      this.errorMessage = 'Time slot already used';
      return;
    }

    if (this.activityForm.startTime.length === 5) {
      this.activityForm.startTime += ':00';
    }
    if (this.activityForm.endTime.length === 5) {
      this.activityForm.endTime += ':00';
    }

    if (this.isEditMode && this.activityForm.id) {
      const activityId = this.activityForm.id;
      const activityData = { ...this.activityForm };

      console.log('📝 Updating activity:', activityId);

      const originalActivity = this.activities.find(a => a.id === activityId);
      const index = this.activities.findIndex(a => a.id === activityId);

      if (index !== -1) {
        this.activities[index] = { ...activityData };
      }

      this.closeModal();

      this.calendarService.updateActivity(activityId, activityData).subscribe({
        next: (updated) => {
          console.log('✅ Successfully updated in backend');
          const idx = this.activities.findIndex(a => a.id === updated.id);
          if (idx !== -1) {
            this.activities[idx] = updated;
          }
        },
        error: (err) => {
          console.error('❌ Backend error:', err);

          if (err.status === 0 || err.status === 401) {
            console.warn('⚠️ Backend not available - changes saved locally only');
          } else {
            console.error('❌ Update failed, restoring original state');
            if (originalActivity && index !== -1) {
              this.activities[index] = originalActivity;
            }
            alert('Error updating activity. Changes reverted.');
          }
        }
      });
    } else {
      this.calendarService.createActivity(this.activityForm).subscribe({
        next: (created) => {
          this.loadActivities();
          this.closeModal();
        },
        error: (err) => {
          console.error('❌ Error creating:', err);
          if (err.status === 0) {
            alert('Cannot create activity: Backend is not available.');
          } else if (err.status === 401) {
            alert('Session expired. Please login again.');
          } else {
            this.errorMessage = 'Error creating activity.';
          }
          this.closeModal();
        }
      });
    }
  }

  deleteActivity(activity: Activity): void {
    console.log('🗑️ Delete button clicked for:', activity);

    if (!activity.id) {
      console.error('❌ No activity ID found!');
      return;
    }

    const isLocalId = activity.id > 1000000000000;

    if (isLocalId) {
      console.warn('⚠️ Local ID detected - activity was never saved in backend');
      this.activities = this.activities.filter(a => a.id !== activity.id);
      console.log('✅ Removed local activity from view');
      return;
    }

    this.activities = this.activities.filter(a => a.id !== activity.id);
    console.log('✅ Removed from view');

    this.calendarService.deleteActivity(activity.id).subscribe({
      next: () => {
        console.log('✅ Successfully deleted from backend');
      },
      error: (err) => {
        console.error('❌ Backend error:', err);

        if (err.status === 0) {
          console.warn('⚠️ Backend not available - activity removed from view only');
        } else if (err.status === 401) {
          console.warn('⚠️ Unauthorized - Please login again');
          alert('Session expired. Please login again to sync with backend.');
        } else if (err.status === 404) {
          console.warn('⚠️ Activity not found in backend (already deleted)');
        } else {
          console.error('❌ Delete failed, but activity removed from view');
          alert('Warning: Activity removed from view but may still exist in backend.');
        }
      }
    });
  }

  timeToMinutes(time: string): number {
    const [hours, minutes] = time.substring(0, 5).split(':').map(Number);
    return hours * 60 + minutes;
  }

  checkOverlap(activityToCheck: Activity): boolean {
    return this.activities.some(activity => {
      if (activity.id === activityToCheck.id) {
        return false;
      }

      if (activity.date !== activityToCheck.date) {
        return false;
      }

      const existingStart = this.timeToMinutes(activity.startTime);
      const existingEnd = this.timeToMinutes(activity.endTime);
      const newStart = this.timeToMinutes(activityToCheck.startTime);
      const newEnd = this.timeToMinutes(activityToCheck.endTime);

      return (newStart < existingEnd && newEnd > existingStart);
    });
  }

  validateForm(): boolean {
    if (
      !this.activityForm.startTime ||
      !this.activityForm.endTime ||
      !this.activityForm.date ||
      !this.activityForm.courseName ||
      !this.activityForm.room ||
      !this.activityForm.professor
    ) {
      this.errorMessage = 'Please fill all required fields';
      return false;
    }

    const startTime = this.activityForm.startTime.substring(0, 5);
    const endTime = this.activityForm.endTime.substring(0, 5);

    const [startHour, startMin] = startTime.split(':').map(Number);
    const [endHour, endMin] = endTime.split(':').map(Number);

    const startMinutes = startHour * 60 + startMin;
    const endMinutes = endHour * 60 + endMin;

    if (startMinutes >= endMinutes) {
      this.errorMessage = 'End time must be after start time';
      return false;
    }

    this.errorMessage = '';
    return true;
  }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData } from 'chart.js';
import { UserExamService } from '../../services/user-exam.service';
import { AuthService } from '../../services/auth';

// serve per i componenti della libreria chart.js
import {
  Chart,
  ArcElement,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  BarController,
  Tooltip,
  Legend
} from 'chart.js';

Chart.register(
  ArcElement,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  BarController,
  Tooltip,
  Legend
);

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit {

  @ViewChild('barChart') barChart?: BaseChartDirective;
  @ViewChild('doughnutChart') doughnutChart?: BaseChartDirective;

  // variabili delle statistiche
  avgWeighted: number = 0;
  avgArithmetic: number = 0;
  totCFU: number = 0;
  earnedCFU: number = 0;
  creditPercent: number = 0;
  projectedGrade: number = 0;
  examsPassed: number = 0;
  examsTotal: number = 0;
  loading: boolean = true;

  // configurazione dei dati per il grafico circolare
  circleData: ChartData<'doughnut'> = {
    labels: ['Acquired', 'Remaining'],
    datasets: [{
      data: [0, 180],
      backgroundColor: ['#5B7FDB', '#E5E7EB'],
      borderWidth: 0
    }]
  };
  circleType: 'doughnut' = 'doughnut';
  circleOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    maintainAspectRatio: true,
    cutout: '75%', //crea l'effetto a cerchio
    plugins: {
      legend: { display: false },
      tooltip: { enabled: false }
    }
  };

  // configurazione dei dati per il grafico a barre
  barData: ChartData<'bar'> = {
    labels: ['18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'],
    datasets: [{
      data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      backgroundColor: '#5B7FDB',
      borderRadius: 4
    }]
  };
  barType: 'bar' = 'bar';
  barOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false }
    },
    scales: {
      y: {
        beginAtZero: true,
        ticks: { stepSize: 1} //mostra i numeri sull'asse y di 1 in 1
      }
    }
  };

  constructor(private examService: UserExamService, private auth: AuthService) { }

  ngOnInit() {
    this.getDashboardData();
  }

  //recupera i dati iniziali verificando chi è loggato
  getDashboardData() {
    this.auth.checkSession().subscribe({
      next: (user: any) => {
        let userId = user.id;
        this.getStats(userId);
        this.getGrades(userId);
      }
    })
  }

  //recupera le statistiche generali dell'utente loggato
  getStats(userId: number) {
    this.examService.getUserStatistics(userId).subscribe({
      next: (stats) => {
        this.avgWeighted = stats.weightedAverage || 0;
        this.avgArithmetic = stats.arithmeticAverage || 0;
        this.totCFU = stats.totalCFU || 180;
        this.earnedCFU = stats.acquiredCFU || 0;
        this.projectedGrade = stats.degreeGradePrediction || 0;
        this.examsPassed = stats.passedExams || 0;
        this.examsTotal = stats.totalExams || 0;

        //calcolo della percentuale di crediti ottenuti
        if (this.totCFU > 0) {
          this.creditPercent = Math.round((this.earnedCFU / this.totCFU) * 100);
        }

        this.updateCircle();
      }
    })
  }

  //recupera la lista degli esami dell'utente e calcola la distribuzione dei voti nel grafico a barre
  getGrades(userId: number) {
    this.examService.getUsersExams(userId).subscribe({
      next: (exams) => {
        let grades = new Array(13).fill(0); //array dove contiamo le occorrenze dei voti da 18 a 30

        for (let i = 0; i < exams.length; i++) {
          let exam = exams[i];
          if (exam.grade && exam.status === 'PASSED') {
            if (exam.grade >= 18 && exam.grade < 30) {
              grades[exam.grade - 18]++;
            } else if (exam.grade === 30) {
              grades[12]++;
            }
          }
        }
        //aggiorno i dati del grafico
        this.barData.datasets[0].data = grades;
        // Object.assign viene usato per triggerare il rilevamento del cambiamento in Angular per i grafici
        this.barData = Object.assign({}, this.barData);
        this.loading = false;
      }
    });
  }

  //aggiorna il grafico circolare
  updateCircle() {
    let left = this.totCFU - this.earnedCFU;
    this.circleData.datasets[0].data = [this.earnedCFU, left];
    this.circleData = Object.assign({}, this.circleData);
  }

}

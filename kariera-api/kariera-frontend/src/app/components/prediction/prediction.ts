import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserExamService } from '../../services/user-exam.service';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-prediction',
  imports: [CommonModule, FormsModule],
  templateUrl: './prediction.html',
  styleUrl: './prediction.css',
})
export class PredictionComponent implements OnInit {

  allExams: any[] = [];  //array contente tutti gli esami dell'utente
  passedExams: any[] = []; //array contenente gli esami passati dell'utente
  notPassedExams: any[] = []; //array contenente gli esami non passati dell'utente'
  hypotheticalExams: any[] = []; //array dove andremo a mettere gli esami dove facciamo la previsione

  currentStats: any = { //variabili che contengono le statistiche dell'utente loggato
    weightedAvg: 0,
    arithmeticAvg: 0,
    acquiredCFU: 0,
    totalCFU: 180,
    projectedGrade: 0,
    passedCount: 0
  };

  predictedStats: any = { //variabili per le statistiche di previsione , uguali a quelle dell'utente
    weightedAvg: 0,
    arithmeticAvg: 0,
    acquiredCFU: 0,
    totalCFU: 180,
    projectedGrade: 0,
    passedCount: 0
  };

  selectedExamId: any = null;  //id dell'esame che andiamo a prendere
  selectedGrade: number = 18;  //di default diamo 18 come voto minimo per gli esami dove facciamo la previsione

  constructor(
    private examService: UserExamService,
    private auth: AuthService
  ) { }

  ngOnInit() {
    this.loadData();
  }

  //recuperiamo i dati che ci servono come l'utente loggato
  loadData() {
    this.auth.checkSession().subscribe({
      next: (user: any) => {
        let userId = user.id;

        this.examService.getUserStatistics(userId).subscribe({
          next: (stats) => {
            //prendiamo le statistiche dell'utente loggato e le salviamo nel nostro oggetto currentStats
            this.currentStats.weightedAvg = stats.weightedAverage || 0;
            this.currentStats.arithmeticAvg = stats.arithmeticAverage || 0;
            this.currentStats.acquiredCFU = stats.acquiredCFU || 0;
            this.currentStats.totalCFU = stats.totalCFU || 180;
            this.currentStats.projectedGrade = stats.degreeGradePrediction || 0;
            this.currentStats.passedCount = stats.passedExams || 0;

            //le statistiche in currentstats le copiamo in predicted in modo che all'inizio tutte e 2 le stats sono uguali
            this.predictedStats.weightedAvg = this.currentStats.weightedAvg;
            this.predictedStats.arithmeticAvg = this.currentStats.arithmeticAvg;
            this.predictedStats.acquiredCFU = this.currentStats.acquiredCFU;
            this.predictedStats.totalCFU = this.currentStats.totalCFU;
            this.predictedStats.projectedGrade = this.currentStats.projectedGrade;
            this.predictedStats.passedCount = this.currentStats.passedCount;
          }
        });

        //prendiamo tutti gli esami dell'utente loggato e li salviamo in allExams
        this.examService.getUsersExams(userId).subscribe({
          next: (exams) => {
            this.allExams = exams;
            this.passedExams = [];
            this.notPassedExams = [];

            //facciamo un filtro degli esami in base al loro status vanno o in un array o in un altro
            for (let i = 0; i < exams.length; i++) {
              if (exams[i].status === 'PASSED') {
                this.passedExams.push(exams[i]);
              } else if (exams[i].status === 'NOT_TAKEN') {
                this.notPassedExams.push(exams[i]);
              }
            }
          }
        });
      }
    });
  }

  //funzione che aggiunge un esame ipotetico all'array delle previsioni
  addHypotheticalExam() {
    //verifico che l'esame  sia stato selezionato e che il voto sia compreso tra 18 e 30'
    if (!this.selectedExamId || this.selectedGrade < 18 || this.selectedGrade > 30) {
      return;
    }

    let examId = Number(this.selectedExamId);
    let exam = null;
    // cerco l'esame nel nostro array di esami non passati
    for (let i = 0; i < this.notPassedExams.length; i++) {
      if (this.notPassedExams[i].id === examId) {
        exam = this.notPassedExams[i];
        break;
      }
    }

    if (!exam) return;
    //evito duplicati nella array delle previsioni
    for (let i = 0; i < this.hypotheticalExams.length; i++) {
      if (this.hypotheticalExams[i].exam.id === exam.id) {
        return;
      }
    }


    this.hypotheticalExams.push({
      exam: exam,
      grade: this.selectedGrade
    });

    this.recalculateStats();

    //dopo aver aggiunto un esame resetto i campi di input di selezione esame e scelta voto
    this.selectedExamId = null;
    this.selectedGrade = 18;
  }

  //funzione che rimuove un esame ipotetico dall'array delle previsioni e riaggiorna le statistiche
  removeHypotheticalExam(index: number) {
    this.hypotheticalExams.splice(index, 1);
    this.recalculateStats();
  }

  //funzione che aggiorna il voto di un esame ipotetico nella array delle previsioni e riaggiorna le statistiche
  updateHypotheticalGrade(index: number, newGrade: number) {
    if (newGrade >= 18 && newGrade <= 30) {
      this.hypotheticalExams[index].grade = newGrade;
      this.recalculateStats();
    }
  }

  //funzione che va a calcolare le statistiche , combinando i dati che sono salvati con quelli ipotetici
  recalculateStats() {
    let totalWeightedGrade = 0;
    let totalCFU = 0;
    let totalGrades = 0;
    let examCount = 0;

    //calcolo delle statistiche per gli esami passati
    for (let i = 0; i < this.passedExams.length; i++) {
      let exam = this.passedExams[i];
      if (exam.grade && exam.grade > 0) {
        totalWeightedGrade = totalWeightedGrade + (exam.grade * exam.cfu);
        totalCFU = totalCFU + exam.cfu;
        totalGrades = totalGrades + exam.grade;
        examCount = examCount + 1;
      }
    }

    //calcolo delle statistiche per gli esami ipotetici usando le statistiche precedenti
    for (let i = 0; i < this.hypotheticalExams.length; i++) {
      let item = this.hypotheticalExams[i];
      totalWeightedGrade = totalWeightedGrade + (item.grade * item.exam.cfu);
      totalCFU = totalCFU + item.exam.cfu;
      totalGrades = totalGrades + item.grade;
      examCount = examCount + 1;
    }

    //se non ci sono esami ipotetici calcolati allora le 2 tabelle si devono corrispondere
    if (examCount === 0) {
      this.predictedStats.weightedAvg = this.currentStats.weightedAvg;
      this.predictedStats.arithmeticAvg = this.currentStats.arithmeticAvg;
      this.predictedStats.acquiredCFU = this.currentStats.acquiredCFU;
      this.predictedStats.projectedGrade = this.currentStats.projectedGrade;
      this.predictedStats.passedCount = this.currentStats.passedCount;
      return;
    }

    //calcolo delle medie e della proiezione di voto
    let weightedAvg = totalWeightedGrade / totalCFU;
    let arithmeticAvg = totalGrades / examCount;
    let projectedGrade = (weightedAvg * 110) / 30;

    //assegno all'oggetto predictedStats le statistiche calcolate
    this.predictedStats.weightedAvg = weightedAvg;
    this.predictedStats.arithmeticAvg = arithmeticAvg;
    this.predictedStats.acquiredCFU = totalCFU;
    this.predictedStats.projectedGrade = projectedGrade;
    this.predictedStats.passedCount = examCount;
  }

  //svuoto la simulazione e resetto tutti i valori predetti a quelli attuali
  resetPrediction() {
    this.hypotheticalExams = [];
    this.predictedStats.weightedAvg = this.currentStats.weightedAvg;
    this.predictedStats.arithmeticAvg = this.currentStats.arithmeticAvg;
    this.predictedStats.acquiredCFU = this.currentStats.acquiredCFU;
    this.predictedStats.projectedGrade = this.currentStats.projectedGrade;
    this.predictedStats.passedCount = this.currentStats.passedCount;
    this.selectedExamId = null;
    this.selectedGrade = 18;
  }

  //calcolo della differenza numerica tra valore attuale e valore previsto  , utile per vedere aumento o diminuzione in termini numeri
  getDifference(current: number, predicted: number) {
    return predicted - current;
  }

  //restituisce true se l'utente ha almeno immesso un esame da prevedere
  hasChanges() {
    return this.hypotheticalExams.length > 0;
  }
}

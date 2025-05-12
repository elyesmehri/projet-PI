import { Component } from '@angular/core';
import { Location } from '@angular/common';

interface Question {
  text: string;
  field: string;
}

@Component({
  selector: 'app-medical-assitant-q',
  templateUrl: './medical-assitant-q.component.html',
  styleUrls: ['./medical-assitant-q.component.css']
})

export class MedicalAssitantQComponent {

  constructor(private location: Location) {}

  questions: Question[] = [
    { text: "Did you experience vision trouble these days?", field: "Ophthalmology" },
    { text: "Do you regularly take medication to clean your bloodstream?", field: "Nephrology" },
    { text: "Have you had persistent joint pain or swelling?", field: "Rheumatology" },
    { text: "Have you felt unusually anxious or depressed?", field: "Psychiatry" },
    { text: "Have you experienced chest pain or shortness of breath?", field: "Cardiology" },
    { text: "Have you noticed any new or changing skin conditions?", field: "Dermatology" },
    { text: "Have you had persistent digestive issues like stomach pain or bloating?", field: "Gastroenterology" },
    { text: "Have you been coughing or having trouble breathing?", field: "Pulmonology" },
    { text: "Have you experienced numbness or weakness in your limbs?", field: "Neurology" },
    { text: "Are you concerned about issues with your ears, nose, or throat?", field: "Otolaryngology (ORL)" }
  ];

  currentQuestionIndex = 0;
  answers: boolean[] = new Array(this.questions.length).fill(null);
  suggestedFields: string[] = [];
  showResults = false;

  answerQuestion(response: boolean): void {
    this.answers[this.currentQuestionIndex] = response;
    if (this.currentQuestionIndex < this.questions.length - 1) {
      this.currentQuestionIndex++;
    } else {
      this.processAnswers();
      this.showResults = true;
    }
  }

  nextQuestion(): void {
    if (this.currentQuestionIndex < this.questions.length - 1) {
      this.currentQuestionIndex++;
    }
  }

  prevQuestion(): void {
    if (this.currentQuestionIndex > 0) {
      this.currentQuestionIndex--;
    }
  }

  processAnswers(): void {
    this.suggestedFields = [];
    for (let i = 0; i < this.questions.length; i++) {
      if (this.answers[i] === true) {
        if (this.questions[i].field) {
          this.suggestedFields.push(<string>this.questions[i].field);
        }
      }
    }
  }

  get currentQuestion(): Question {
    return this.questions[this.currentQuestionIndex];
  }

  get isFirstQuestion(): boolean {
    return this.currentQuestionIndex === 0;
  }

  get isLastQuestion(): boolean {
    return this.currentQuestionIndex === this.questions.length - 1;
  }

  goBack(): void {
    this.location.back();
  }
}

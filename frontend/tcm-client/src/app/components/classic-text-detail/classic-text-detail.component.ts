import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ClassicTextService } from '../../services/classic-text.service';
import { UserNoteService } from '../../services/user-note.service';
import { IncorrectAnswerService } from '../../services/incorrect-answer.service';
import { ClassicText } from '../../models/classic-text.model';
import { UserNote } from '../../models/user-note.model';
import { IncorrectAnswer } from '../../models/incorrect-answer.model';

@Component({
  selector: 'app-classic-text-detail',
  templateUrl: './classic-text-detail.component.html',
  styleUrls: ['./classic-text-detail.component.scss']
})
export class ClassicTextDetailComponent implements OnInit {
  text: ClassicText | null = null;
  notes: UserNote[] = [];
  incorrectAnswers: IncorrectAnswer[] = [];
  newNoteContent = '';
  userAnswer = '';
  showAnswer = false;

  constructor(
    private route: ActivatedRoute,
    private classicTextService: ClassicTextService,
    private userNoteService: UserNoteService,
    private incorrectAnswerService: IncorrectAnswerService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const textId = +params['id'];
      this.loadText(textId);
      this.loadNotes(textId);
      this.loadIncorrectAnswers(textId);
    });
  }

  private loadText(textId: number): void {
    this.classicTextService.getTextById(textId)
      .subscribe(text => this.text = text);
  }

  private loadNotes(textId: number): void {
    this.userNoteService.getUserNotesByClassicText(textId)
      .subscribe(notes => this.notes = notes);
  }

  private loadIncorrectAnswers(textId: number): void {
    this.incorrectAnswerService.getUserIncorrectAnswersByClassicText(textId)
      .subscribe(answers => this.incorrectAnswers = answers);
  }

  addNote(): void {
    if (this.text && this.newNoteContent.trim()) {
      this.userNoteService.createNote(this.text.id!, this.newNoteContent)
        .subscribe(note => {
          this.notes.push(note);
          this.newNoteContent = '';
        });
    }
  }

  updateNote(note: UserNote, content: string): void {
    this.userNoteService.updateNote(note.id!, content)
      .subscribe(updatedNote => {
        const index = this.notes.findIndex(n => n.id === updatedNote.id);
        if (index !== -1) {
          this.notes[index] = updatedNote;
        }
      });
  }

  deleteNote(noteId: number): void {
    this.userNoteService.deleteNote(noteId)
      .subscribe(() => {
        this.notes = this.notes.filter(note => note.id !== noteId);
      });
  }

  submitAnswer(): void {
    if (this.text && this.userAnswer.trim()) {
      this.incorrectAnswerService.recordIncorrectAnswer(
        this.text.id!,
        this.userAnswer,
        this.text.content
      ).subscribe(answer => {
        this.incorrectAnswers.push(answer);
        this.userAnswer = '';
        this.showAnswer = true;
      });
    }
  }

  markAsReviewed(answerId: number): void {
    this.incorrectAnswerService.markAsReviewed(answerId)
      .subscribe(() => {
        const answer = this.incorrectAnswers.find(a => a.id === answerId);
        if (answer) {
          answer.reviewed = true;
        }
      });
  }
}
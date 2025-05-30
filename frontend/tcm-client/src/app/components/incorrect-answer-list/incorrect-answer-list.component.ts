import { Component, OnInit } from '@angular/core';
import { IncorrectAnswerService } from '../../services/incorrect-answer.service';
import { IncorrectAnswer } from '../../models/incorrect-answer.model';
import { PageResponse } from '../../models/page-response.model';

@Component({
  selector: 'app-incorrect-answer-list',
  templateUrl: './incorrect-answer-list.component.html',
  styleUrls: ['./incorrect-answer-list.component.scss']
})
export class IncorrectAnswerListComponent implements OnInit {
  answers: IncorrectAnswer[] = [];
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  showUnreviewedOnly = false;
  unreviewedCount = 0;

  constructor(private incorrectAnswerService: IncorrectAnswerService) { }

  ngOnInit(): void {
    this.loadAnswers();
    this.loadUnreviewedCount();
  }

  loadAnswers(): void {
    if (this.showUnreviewedOnly) {
      this.loadUnreviewedAnswers();
    } else {
      this.loadAllAnswers();
    }
  }

  private loadAllAnswers(): void {
    this.incorrectAnswerService.getUserIncorrectAnswers(this.currentPage, this.pageSize)
      .subscribe(response => this.handlePageResponse(response));
  }

  private loadUnreviewedAnswers(): void {
    this.incorrectAnswerService.getUserUnreviewedAnswers(this.currentPage, this.pageSize)
      .subscribe(response => this.handlePageResponse(response));
  }

  private loadUnreviewedCount(): void {
    this.incorrectAnswerService.getUnreviewedCount()
      .subscribe(response => this.unreviewedCount = response.count);
  }

  private handlePageResponse(response: PageResponse<IncorrectAnswer>): void {
    this.answers = response.content;
    this.totalElements = response.totalElements;
    this.totalPages = response.totalPages;
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadAnswers();
  }

  toggleUnreviewedOnly(): void {
    this.currentPage = 0;
    this.loadAnswers();
  }

  markAsReviewed(answerId: number): void {
    this.incorrectAnswerService.markAsReviewed(answerId)
      .subscribe(() => {
        const answer = this.answers.find(a => a.id === answerId);
        if (answer) {
          answer.reviewed = true;
          this.unreviewedCount--;
        }
      });
  }
}
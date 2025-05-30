import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { IncorrectAnswer } from '../models/incorrect-answer.model';
import { PageResponse } from '../models/page-response.model';

@Injectable({
  providedIn: 'root'
})
export class IncorrectAnswerService {
  private apiUrl = `${environment.apiUrl}/api/incorrect-answers`;

  constructor(private http: HttpClient) { }

  getUserIncorrectAnswers(page: number = 0, size: number = 10): Observable<PageResponse<IncorrectAnswer>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<IncorrectAnswer>>(this.apiUrl, { params });
  }

  getUserUnreviewedAnswers(page: number = 0, size: number = 10): Observable<PageResponse<IncorrectAnswer>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<IncorrectAnswer>>(`${this.apiUrl}/unreviewed`, { params });
  }

  getUserIncorrectAnswersByClassicText(textId: number): Observable<IncorrectAnswer[]> {
    return this.http.get<IncorrectAnswer[]>(`${this.apiUrl}/text/${textId}`);
  }

  recordIncorrectAnswer(textId: number, userAnswer: string, correctAnswer: string): Observable<IncorrectAnswer> {
    return this.http.post<IncorrectAnswer>(`${this.apiUrl}/text/${textId}`, {
      userAnswer,
      correctAnswer
    });
  }

  markAsReviewed(answerId: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${answerId}/review`, {});
  }

  getUnreviewedCount(): Observable<{ count: number }> {
    return this.http.get<{ count: number }>(`${this.apiUrl}/unreviewed/count`);
  }
}
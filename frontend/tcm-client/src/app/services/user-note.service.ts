import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserNote } from '../models/user-note.model';
import { PageResponse } from '../models/page-response.model';

@Injectable({
  providedIn: 'root'
})
export class UserNoteService {
  private apiUrl = `${environment.apiUrl}/api/notes`;

  constructor(private http: HttpClient) { }

  getUserNotes(page: number = 0, size: number = 10): Observable<PageResponse<UserNote>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<UserNote>>(this.apiUrl, { params });
  }

  getUserNotesByClassicText(textId: number): Observable<UserNote[]> {
    return this.http.get<UserNote[]>(`${this.apiUrl}/text/${textId}`);
  }

  createNote(textId: number, content: string): Observable<UserNote> {
    return this.http.post<UserNote>(`${this.apiUrl}/text/${textId}`, { content });
  }

  updateNote(noteId: number, content: string): Observable<UserNote> {
    return this.http.put<UserNote>(`${this.apiUrl}/${noteId}`, { content });
  }

  deleteNote(noteId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${noteId}`);
  }
}
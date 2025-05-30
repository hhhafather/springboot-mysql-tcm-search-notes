import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ClassicText } from '../models/classic-text.model';
import { PageResponse } from '../models/page-response.model';

@Injectable({
  providedIn: 'root'
})
export class ClassicTextService {
  private apiUrl = `${environment.apiUrl}/api/texts`;

  constructor(private http: HttpClient) { }

  getAllTexts(page: number = 0, size: number = 10): Observable<PageResponse<ClassicText>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<ClassicText>>(this.apiUrl, { params });
  }

  getTextById(id: number): Observable<ClassicText> {
    return this.http.get<ClassicText>(`${this.apiUrl}/${id}`);
  }

  getTextsByCategory(category: string, page: number = 0, size: number = 10): Observable<PageResponse<ClassicText>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<ClassicText>>(`${this.apiUrl}/category/${category}`, { params });
  }

  searchTexts(keyword: string, page: number = 0, size: number = 10): Observable<PageResponse<ClassicText>> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageResponse<ClassicText>>(`${this.apiUrl}/search`, { params });
  }

  createText(text: ClassicText): Observable<ClassicText> {
    return this.http.post<ClassicText>(this.apiUrl, text);
  }

  updateText(id: number, text: ClassicText): Observable<ClassicText> {
    return this.http.put<ClassicText>(`${this.apiUrl}/${id}`, text);
  }

  deleteText(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
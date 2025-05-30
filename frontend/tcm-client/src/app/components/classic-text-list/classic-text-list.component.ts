import { Component, OnInit } from '@angular/core';
import { ClassicTextService } from '../../services/classic-text.service';
import { ClassicText } from '../../models/classic-text.model';
import { PageResponse } from '../../models/page-response.model';

@Component({
  selector: 'app-classic-text-list',
  templateUrl: './classic-text-list.component.html',
  styleUrls: ['./classic-text-list.component.scss']
})
export class ClassicTextListComponent implements OnInit {
  texts: ClassicText[] = [];
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  searchKeyword = '';
  selectedCategory = '';
  categories = ['伤寒论', '金匮要略', '温病学', '其他'];

  constructor(private classicTextService: ClassicTextService) { }

  ngOnInit(): void {
    this.loadTexts();
  }

  loadTexts(): void {
    if (this.searchKeyword) {
      this.searchTexts();
    } else if (this.selectedCategory) {
      this.loadTextsByCategory();
    } else {
      this.loadAllTexts();
    }
  }

  private loadAllTexts(): void {
    this.classicTextService.getAllTexts(this.currentPage, this.pageSize)
      .subscribe(response => this.handlePageResponse(response));
  }

  private loadTextsByCategory(): void {
    this.classicTextService.getTextsByCategory(this.selectedCategory, this.currentPage, this.pageSize)
      .subscribe(response => this.handlePageResponse(response));
  }

  searchTexts(): void {
    if (this.searchKeyword.trim()) {
      this.classicTextService.searchTexts(this.searchKeyword, this.currentPage, this.pageSize)
        .subscribe(response => this.handlePageResponse(response));
    }
  }

  private handlePageResponse(response: PageResponse<ClassicText>): void {
    this.texts = response.content;
    this.totalElements = response.totalElements;
    this.totalPages = response.totalPages;
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadTexts();
  }

  onCategoryChange(): void {
    this.currentPage = 0;
    this.loadTexts();
  }

  onSearch(): void {
    this.currentPage = 0;
    this.selectedCategory = '';
    this.loadTexts();
  }

  clearSearch(): void {
    this.searchKeyword = '';
    this.currentPage = 0;
    this.loadTexts();
  }
}
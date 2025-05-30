export interface IncorrectAnswer {
  id?: number;
  userId: number;
  classicTextId: number;
  userAnswer: string;
  correctAnswer: string;
  reviewed: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}
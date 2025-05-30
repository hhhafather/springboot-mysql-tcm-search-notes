export interface UserNote {
  id?: number;
  userId: number;
  classicTextId: number;
  content: string;
  createdAt?: Date;
  updatedAt?: Date;
}
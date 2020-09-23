import { AuthorModel } from './author.model';

export interface ReputationModel {
  blogs: { monthly: number; allTime: number; };
  knolx: { monthly: number; allTime: number; };
  webinars: { monthly: number; allTime: number; };
  techhubTemplate: { monthly: number; allTime: number; };
  osContribution: { monthly: number; allTime: number; };
  conferences: { monthly: number; allTime: number; };
  books: { monthly: number; allTime: number; };
  researchPapers: { monthly: number; allTime: number; };
  reputation: AuthorModel[];
}

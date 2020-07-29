import {AuthorModel} from './author.model';

export interface ReputationModel {
    monthlyBlogCount: number;
    monthlyKnolxCount: number;
    monthlyWebinarCount: number;
    allTimeBlogCount: number;
    allTimeKnolxCount: number;
    allTimeWebinarCount: number;
    reputation: AuthorModel[];
}

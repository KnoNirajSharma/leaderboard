import {AuthorModel} from './author.model';

export interface ReputationModel {
    monthlyBlogCount: number;
    monthlyKnolxCount: number;
    allTimeBlogCount: number;
    allTimeKnolxCount: number;
    reputationData: AuthorModel[];
}

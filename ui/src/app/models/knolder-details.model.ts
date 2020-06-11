import {ScoreBreakDownModel} from './ScoreBreakDown.model';

export interface KnolderDetailsModel {
    knolderName: string;
    score: number;
    scoreBreakDown: ScoreBreakDownModel[];
}

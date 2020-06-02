export interface KnolderDetailsModel {
    knolderName?: string;
    currentMonthAndYear?: string;
    allTimeScore?: number;
    monthlyScore?: number;
    blogScore?: number;
    blogDetails: [
        {
            title?: string,
            date?: string
        }
    ];
}

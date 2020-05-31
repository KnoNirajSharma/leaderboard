export interface KnolderDetailsModel {
    knolderName?: string;
    allTimeScore?: number;
    monthlyScore?: number;
    blogScore?: number;
    'blogDetails': [
        {
            'title'?: string,
            'date'?: string
        }
    ];
}

import {UserAccountDetailsModel} from './user-account-details.model';

export interface AdminActionModel {
    action: string;
    userAccountDetails: UserAccountDetailsModel;
}

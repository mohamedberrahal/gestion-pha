import { Moment } from 'moment';
import { IMaladie } from 'app/shared/model/maladie.model';
import { IEffet } from 'app/shared/model/effet.model';
import { IDestination } from 'app/shared/model/destination.model';

export interface IMedicament {
    id?: number;
    nom?: string;
    libelle?: string;
    discription?: string;
    prix?: number;
    dateExpiraction?: Moment;
    maladie?: IMaladie;
    effets?: IEffet[];
    destinations?: IDestination[];
}

export class Medicament implements IMedicament {
    constructor(
        public id?: number,
        public nom?: string,
        public libelle?: string,
        public discription?: string,
        public prix?: number,
        public dateExpiraction?: Moment,
        public maladie?: IMaladie,
        public effets?: IEffet[],
        public destinations?: IDestination[]
    ) {}
}

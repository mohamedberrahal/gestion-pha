import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEffet } from 'app/shared/model/effet.model';

type EntityResponseType = HttpResponse<IEffet>;
type EntityArrayResponseType = HttpResponse<IEffet[]>;

@Injectable({ providedIn: 'root' })
export class EffetService {
    public resourceUrl = SERVER_API_URL + 'api/effets';

    constructor(protected http: HttpClient) {}

    create(effet: IEffet): Observable<EntityResponseType> {
        return this.http.post<IEffet>(this.resourceUrl, effet, { observe: 'response' });
    }

    update(effet: IEffet): Observable<EntityResponseType> {
        return this.http.put<IEffet>(this.resourceUrl, effet, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEffet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEffet[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TownResponse } from '../models/town.model';
import { CampaignStatus } from '../models/campaign.model';

@Injectable({
  providedIn: 'root'
})
export class DictionaryService {
  private readonly http = inject(HttpClient);

  getTowns(): Observable<TownResponse[]> {
    return this.http.get<TownResponse[]>('/api/towns');
  }

  getKeywords(): Observable<string[]> {
    return this.http.get<string[]>('/api/keywords');
  }

  getStatuses(): Observable<CampaignStatus[]> {
    return this.http.get<CampaignStatus[]>('/api/campaigns/statuses');
  }
}
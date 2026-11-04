import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { CampaignRequest, CampaignResponse } from '../models/campaign.model';
import { EmeraldAccountService } from './emerald-account.service';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {
  private readonly http = inject(HttpClient);
  private readonly emeraldAccountService = inject(EmeraldAccountService);
  private readonly apiUrl = '/api/campaigns';

  getAllCampaigns(): Observable<CampaignResponse[]> {
    return this.http.get<CampaignResponse[]>(this.apiUrl);
  }

  getCampaignById(id: number): Observable<CampaignResponse> {
    return this.http.get<CampaignResponse>(`${this.apiUrl}/${id}`);
  }

  createCampaign(request: CampaignRequest): Observable<CampaignResponse> {
    return this.http.post<CampaignResponse>(this.apiUrl, request).pipe(
      tap(() => this.emeraldAccountService.fetchBalance().subscribe())
    );
  }

  updateCampaign(id: number, request: CampaignRequest): Observable<CampaignResponse> {
    return this.http.put<CampaignResponse>(`${this.apiUrl}/${id}`, request).pipe(
      tap(() => this.emeraldAccountService.fetchBalance().subscribe())
    );
  }

  deleteCampaign(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => this.emeraldAccountService.fetchBalance().subscribe())
    );
  }
}
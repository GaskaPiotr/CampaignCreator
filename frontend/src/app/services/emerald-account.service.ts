import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { EmeraldAccountResponse } from '../models/emerald-account.model';

@Injectable({
  providedIn: 'root'
})
export class EmeraldAccountService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/emerald-account';

  readonly balance = signal<number | null>(null);

  fetchBalance(): Observable<EmeraldAccountResponse> {
    return this.http.get<EmeraldAccountResponse>(this.apiUrl).pipe(
      tap(response => this.balance.set(response.balance))
    );
  }
}
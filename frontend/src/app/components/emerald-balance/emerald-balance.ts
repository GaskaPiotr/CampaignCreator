import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmeraldAccountService } from '../../services/emerald-account.service';

@Component({
  selector: 'app-emerald-balance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './emerald-balance.html',
  styleUrl: './emerald-balance.css',
})
export class EmeraldBalance implements OnInit {
  private readonly emeraldAccountService = inject(EmeraldAccountService);

  readonly balance = this.emeraldAccountService.balance;

  ngOnInit(): void {
    this.emeraldAccountService.fetchBalance().subscribe();
  }
}

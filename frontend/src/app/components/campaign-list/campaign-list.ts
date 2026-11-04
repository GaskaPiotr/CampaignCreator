import { Component, EventEmitter, OnInit, Output, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CampaignResponse } from '../../models/campaign.model';
import { CampaignService } from '../../services/campaign.service';

@Component({
  selector: 'app-campaign-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './campaign-list.html',
  styleUrl: './campaign-list.css'
})
export class CampaignListComponent implements OnInit {
  private readonly campaignService = inject(CampaignService);

  @Output() edit = new EventEmitter<CampaignResponse>();

  readonly campaigns = signal<CampaignResponse[]>([]);

  ngOnInit(): void {
    this.loadCampaigns();
  }

  loadCampaigns(): void {
    this.campaignService.getAllCampaigns().subscribe({
      next: (data: CampaignResponse[]) => {
        this.campaigns.set(data);
      },
      error: (err) => console.error('Failed to load campaigns:', err)
    });
  }

  onEdit(campaign: CampaignResponse): void {
    this.edit.emit(campaign);
  }

  onDelete(id: number): void {
    if (confirm(`Are you sure you want to delete campaign #${id}? Funds will be refunded.`)) {
      this.campaignService.deleteCampaign(id).subscribe(() => {
        this.loadCampaigns();
      });
    }
  }
}
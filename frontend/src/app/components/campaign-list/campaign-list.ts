import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CampaignResponse } from '../../models/campaign.model';
import { CampaignService } from '../../services/campaign.service';
import { DictionaryService } from '../../services/dictionary.service';

@Component({
  selector: 'app-campaign-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './campaign-list.html',
  styleUrl: './campaign-list.css'
})
export class CampaignListComponent implements OnInit {
  private readonly campaignService = inject(CampaignService);
  private readonly dictionaryService = inject(DictionaryService);

  @Output() edit = new EventEmitter<CampaignResponse>();

  campaigns: CampaignResponse[] = [];
  townsMap = new Map<number, string>();

  ngOnInit(): void {
    this.dictionaryService.getTowns().subscribe(towns => {
      towns.forEach(t => this.townsMap.set(t.id, t.name));
      this.loadCampaigns();
    });
  }

  loadCampaigns(): void {
    this.campaignService.getAllCampaigns().subscribe(data => {
      this.campaigns = data;
    });
  }

  getTownName(townId?: number | null): string {
    if (!townId) return '—';
    return this.townsMap.get(townId) || `ID: ${townId}`;
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
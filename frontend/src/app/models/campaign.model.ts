export type CampaignStatus = 'ON' | 'OFF';

export interface CampaignRequest {
  name: string;
  keywords: string[];
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townId?: number | null;
  radiusKm: number;
}

export interface CampaignResponse {
  id: number;
  name: string;
  keywords: string[];
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townName?: string | null;
  radiusKm: number;
}
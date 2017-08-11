export interface Chat {
  id: string,
  text: string,
  name: string,
  timestamp?: string,
  classification?: string,
  result?: any,
  cart?: any,
  selectedTheme?: string,
  priceDetermined?: Boolean
}
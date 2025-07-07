import {Order} from "@/api/Type";

export function formatOrderTime(timestampInSeconds: number): string {
  const date = new Date(timestampInSeconds * 1000); // Convert từ giây → milliseconds

  const weekdays = [
    "Chủ nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư",
    "Thứ Năm", "Thứ Sáu", "Thứ Bảy"
  ];

  const weekday = weekdays[date.getDay()];
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();

  const hour = String(date.getHours()).padStart(2, "0");
  const minute = String(date.getMinutes()).padStart(2, "0");

  return `${weekday}, ${day}/${month}/${year} - ${hour}:${minute}`;
}

export function formatFullAddressFromOrder(recipient: Order["recipient_address"]): string {
  const lines: string[] = [];

  if (recipient.name) lines.push(recipient.name);
  if (recipient.phone_number) lines.push(recipient.phone_number);
  if (recipient.address_detail?.trim()) lines.push(recipient.address_detail);

  if (recipient.district_info?.length) {
    const location = recipient.district_info.map(d => d.address_name).join(", ");
    lines.push(location);
  }
  if(recipient.postal_code?.trim()) lines.push(recipient.postal_code);

  return lines.join("<br />");
}


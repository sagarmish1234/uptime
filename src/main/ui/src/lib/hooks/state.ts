import { MonitorType } from '@/components/monitors/Monitors';
import { Table } from '@tanstack/react-table';
import { create } from 'zustand';

interface TableState {
  table: Table<MonitorType> | null;
  setTable: (t: Table<MonitorType>) => void;
}
export const useTableStore = create<TableState>()((set) => ({
  table: null,
  setTable: (tableVal) => set(() => ({ table: tableVal })),
}));

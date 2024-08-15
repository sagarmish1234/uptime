import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Target } from 'lucide-react';
import { MonitorType } from '../monitors/Monitors';
import PulsingCircle from '../pulsingcircle/PulsingCircle';
import MonitorActions from '@/components/monitoraction/MonitorActions';
import { useTableStore } from '@/lib/hooks/state';
import { useEffect } from 'react';
import {
  ColumnDef,
  useReactTable,
  getCoreRowModel,
  flexRender,
  getFilteredRowModel,
  ColumnFiltersState,
} from '@tanstack/react-table';
import { cn, formatFrequency } from '@/lib/utils';
import { useState } from 'react';
type StatusType = string;
type ColorMap = {
  [key in StatusType]: {
    text: string;
    background: string;
  };
};

const colorMap: ColorMap = {
  UP: {
    text: 'text-green-600',
    background: 'bg-green-600',
  },
  DOWN: {
    text: 'text-red-600',
    background: 'bg-red-600',
  },
  PAUSED: {
    text: 'text-amber-600',
    background: 'bg-amber-600',
  },
};

const MonitorsTable = ({ monitors }: { monitors: MonitorType[] }) => {
  const [columnFilters, setColumnFilters] =
    useState<ColumnFiltersState>([]);
  const { setTable } = useTableStore();

  const getPulseColor = (currentStatus: string) => {
    return colorMap[currentStatus].background;
  };

  const getStatusColor = (status: string) => {
    return colorMap[status].text;
  };

  const formatUrl = (url: string) => {
    return url.split('https://')[1];
  };
  const columns: ColumnDef<MonitorType>[] = [
    {
      accessorKey: 'id',
      header: 'Monitors',
      cell: ({ row }) => {
        return (
          <TableCell className="flex justify-start relative rounded-l-lg w-10">
            <PulsingCircle
              className={cn(
                'left-10',
                getPulseColor(row.getValue('currentStatus'))
              )}
            />
          </TableCell>
        );
      },
    },
    {
      accessorKey: 'url',
      header: '',
      cell: ({ row }) => {
        return (
          <TableCell className="font-medium text-left">
            {formatUrl(row.getValue('url'))}
          </TableCell>
        );
      },
    },
    {
      accessorKey: 'currentStatus',
      header: '',
      cell: ({ row }) => {
        return (
          <TableCell
            className={
              'font-semibold ' +
              getStatusColor(row.getValue('currentStatus'))
            }
          >
            {' '}
            {row.getValue('currentStatus')}
          </TableCell>
        );
      },
    },
    {
      accessorKey: 'checkFrequency',
      header: '',
      cell: ({ row }) => {
        return (
          <TableCell className=" text-[grey]">
            {' '}
            <div className="flex gap-1 place-items-center">
              <Target size={18} />
              {formatFrequency(row.getValue('checkFrequency'))}
            </div>
          </TableCell>
        );
      },
    },
    {
      id: 'actions',
      cell: ({ row }) => {
        return (
          <TableCell>
            <MonitorActions monitor={row.original} />
          </TableCell>
        );
      },
    },
  ];

  const table = useReactTable({
    data: monitors,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnFiltersChange: setColumnFilters,
    state: {
      columnFilters,
    },
  });
  useEffect(() => {
    setTable(table);
  }, [table, setTable]);

  return (
    <div className="border-[1px] mt-10 rounded-xl overflow-hidden">
      <Table>
        {/* <TableCaption>A list of your recent invoices.</TableCaption> */}
        <TableHeader>
          {table &&
            table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id} className="w-10">
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
        </TableHeader>
        <TableBody>
          {monitors &&
            table.getRowModel().rows.map((row) => (
              <TableRow
                key={row.id}
                data-state={row.getIsSelected() && 'selected'}
              >
                {row.getVisibleCells().map((cell) => (
                  <>
                    {flexRender(
                      cell.column.columnDef.cell,
                      cell.getContext()
                    )}
                  </>
                ))}
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default MonitorsTable;

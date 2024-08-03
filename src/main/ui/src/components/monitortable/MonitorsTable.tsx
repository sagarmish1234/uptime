import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { MoreHorizontal, Target } from 'lucide-react';
import { MonitorType } from '../monitors/Monitors';
import PulsingCircle from '../pulsingcircle/PulsingCircle';
import {
  ColumnDef,
  useReactTable,
  getCoreRowModel,
  flexRender,
} from '@tanstack/react-table';
import { cn } from '@/lib/utils';
import { Button } from '../ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
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
  console.log(monitors);

  const formatFrequency = (frequency: string) => {
    const tokens = frequency.split('_');
    return `${tokens[1]}${tokens[2][0].toLocaleLowerCase()}`;
  };

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
      accessorKey: 'currentStatus',
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
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="h-8 w-8 p-0">
                  <span className="sr-only">Open menu</span>
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuLabel>Actions</DropdownMenuLabel>
                <DropdownMenuItem
                  onClick={() =>
                    navigator.clipboard.writeText(row.id)
                  }
                >
                  Copy payment ID
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem>View customer</DropdownMenuItem>
                <DropdownMenuItem>
                  View payment details
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </TableCell>
        );
      },
    },
  ];

  const table = useReactTable({
    data: monitors,
    columns,
    getCoreRowModel: getCoreRowModel(),
  });

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
          {/* <TableHead className="w-[100px]">Monitors</TableHead> */}
          {/* <TableHead>Status</TableHead> */}
          {/* <TableHead>Method</TableHead> */}
          {/* <TableHead className="text-right">Amount</TableHead> */}
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

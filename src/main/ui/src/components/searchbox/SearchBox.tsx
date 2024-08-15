import { Input } from '../ui/input';
import { Search } from 'lucide-react';
import { Space } from 'lucide-react';
import { createRef, useEffect } from 'react';
import { useTableStore } from '@/lib/hooks/state';
import { useState } from 'react';

const SearchBox = () => {
  const ref = createRef<HTMLInputElement>();
  const [filterValue, setFilterValue] = useState('');
  useEffect(() => {
    document.addEventListener('keypress', (event) => {
      if (event.code == 'Space') {
        event.preventDefault();
        ref.current?.focus();
        console.log({ filterValue });
      }
    });
  }, [ref]);

  const { table } = useTableStore();
  useEffect(() => {
    table?.getColumn('url')?.setFilterValue(filterValue);
  }, [filterValue]);
  return (
    <div className="flex">
      <Input
        startIcon={Search}
        placeholder="Search"
        endIcon={<Space size={18} />}
        value={filterValue}
        onChange={(event) => {
          setFilterValue(event.target.value);
        }}
        ref={ref}
      />
    </div>
  );
};

export default SearchBox;

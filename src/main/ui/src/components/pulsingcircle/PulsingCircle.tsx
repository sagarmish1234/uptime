import './circleanimation.css';
const PulsingCircle = ({ className }: { className: string }) => {
  return (
    <div className="relative">
      <div
        className={`circle ${className}`}
        style={{ animationDelay: '0s' }}
      ></div>
      <div
        className={`circle ${className}`}
        style={{ animationDelay: '1s' }}
      ></div>
      <div
        className={`circle ${className}`}
        style={{ animationDelay: '2s' }}
      ></div>
    </div>
  );
};

export default PulsingCircle;

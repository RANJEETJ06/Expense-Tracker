interface TopbarProps {
  title: string;
}

const Topbar: React.FC<TopbarProps> = ({ title }) => {
  return (
    <div className="p-3 flex justify-center text-3xl gap-4 flex-wrap font-semibold">
      {title}
    </div>
  );
};

export default Topbar;

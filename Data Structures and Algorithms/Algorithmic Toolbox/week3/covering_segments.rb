#!/usr/bin/env ruby
# by Andronik Ordian

Segment = Struct.new("Segment", :start, :end)

def optimal_points(segments)
  points = []
  sorted_segments =  segments.sort {|a,b| a[1] <=> b[1]}
  #p sorted_segments
  while true
    first_item = sorted_segments.delete_at(0)
    return points if first_item.nil?
    sorted_segments.select! {|item| item[0] > first_item[1]}
    points.push(first_item[1])
  end
end  

=begin
def time_test()
  #测试次数
  count = 1000

  count.times do 
    segments = []
    n = (rand(100-50)+50).to_i
    n.times do 
      segments.push ([(rand(10**7-0)+0).to_i,(rand() * (10 **9 - 10**9-10**8) + 10**8).to_i])
    end

    points = optimal_points(segments)
    puts "#{points.size}"
    puts "#{points.join(' ')}"
    puts ""
  end
end

time_test()
=end

if __FILE__ == $0
  data = STDIN.read.split().map(&:to_i)
  n = data[0]
  segments = data[1..2*n].each_slice(2).to_a.map { |e| Segment.new(e[0], e[1]) }
  points = optimal_points(segments)
  puts "#{points.size}"
  puts "#{points.join(' ')}"
end